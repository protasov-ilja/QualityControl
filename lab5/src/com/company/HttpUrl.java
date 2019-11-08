package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Protocol {
     HTTP,
     HTTPS
};

public class HttpUrl {
        private String _url;
        private String _domain;
        private String _document;
        private int _port;
        private Protocol _protocol;

        public HttpUrl(String url) throws IllegalArgumentException {
                _url = url;
                parseUrl(url);
        }

        public HttpUrl(String protocol, String domain, String port, String document) {
                validateProtocol(protocol);
                validateDomain(domain);
                validateDocument(document);
                validatePort(port);
                _url = getStringFromProtocol() + "://" + getDomain() + ":" + getPort() + "/" + getDocument();
//        parseUrl(url);
        }

        public HttpUrl(String protocol, String domain, String document) {
                validateProtocol(protocol);
                validateDomain(domain);
                validateDocument(document);
                validatePort("");
                _url = getStringFromProtocol() + "://" + getDomain() + ":" + getPort() + "/" + getDocument();
        }

        private void parseUrl(String url) throws IllegalArgumentException {
                if (url.isEmpty()) {
                        throw new IllegalArgumentException("Empty url");
                }

                Pattern pattern = Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
                Matcher m = pattern.matcher(url);
                if (!m.find()) {
                        throw new IllegalArgumentException("Incorrect url");
                }

                validateProtocol();
                validateDomain();
                validatePort();
                validateDocument();
        }

        private Protocol validateProtocol(String protocolStr) throws IllegalArgumentException {
                if (protocolStr.equals("http")) {
                        return Protocol.HTTP;
                } else if (protocolStr.equals("https")){
                        return Protocol.HTTPS;
                } else {
                        throw new IllegalArgumentException("Invalid type of protocol");
                }
        }

        private void validateDomain(String domain) throws IllegalArgumentException {
                Pattern pattern = Pattern.compile("([A-Za-z0-9]{1,}[\\-]{0,1}[A-Za-z0-9]{1,}[\\.]{0,1}[A-Za-z0-9]{1,})+");
                Matcher m = pattern.matcher(domain);
                if (!m.find() || domain.contains("/")) {
                        throw new IllegalArgumentException("Incorrect domain");
                } else {
                        _domain = domain;
                }
        }

        private void validateDocument(String document) throws IllegalArgumentException {
                if (document.contains("//")) {
                        throw new IllegalArgumentException("Incorrect document");
                } else {
                        _document =  document;
                }
        }

        private void validatePort(String port) throws IllegalArgumentException {
                if (_protocol ==Protocol.HTTP && port.isEmpty()) {
                        _port = 80;
                } else if (_protocol ==Protocol.HTTPS && port.isEmpty()) {
                        _port = 443;
                }
                try {
                        _port = new Integer(port);
                } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Incorrect port");
                }
                if (!(_port > 0 && _port < 3000)) {
                        throw new IllegalArgumentException("Value of port is out of range");
                }
        }

        private void validateProtocol() throws IllegalArgumentException {
                String protocolStr = _url.substring(0, 5);
                if (protocolStr.equals("https")) {
                        _protocol = Protocol.HTTPS;
                } else if (protocolStr.equals("http:")) {
                        _protocol = Protocol.HTTP;
                } else {
                        throw new IllegalArgumentException("Invalid type of protocol");
                }
        }

        private void validateDomain() {
                int pos = _url.indexOf("://") + 3;
                String str = _url.substring(pos);
                String domainStr;
                if (str.contains(":")) {
                        pos = str.indexOf(":");
                        domainStr = str.substring(0, pos);
                } else if (str.contains("/")){
                        pos = str.indexOf("/");
                        domainStr = str.substring(0, pos);
                } else {
                        domainStr = str;
                }
                if (domainStr.isEmpty()) {
                        throw new IllegalArgumentException("Empty domain");
                } else {
                        _domain = domainStr;
                }
        }

        private void validatePort() throws IllegalArgumentException {
                int pos = _url.indexOf(_domain) + _domain.length();
                if (pos != _url.length()) {
                        if (_url.charAt(pos) != ':' && _protocol == Protocol.HTTP) {
                                _port = 80;
                        } else if (_url.charAt(pos) != ':' && _protocol == Protocol.HTTPS) {
                                _port = 443;
                        } else {
                                try {
                                        String str = _url.substring(pos);
                                        String portStr = "";
                                        char symbol = ' ';
                                        int i = 1;
                                        while (symbol != '/' && i < str.length()) {
                                                symbol = str.charAt(i);
                                                if (symbol != '/') {
                                                        portStr += symbol;
                                                }
                                                i++;
                                        }
                                        _port = new Integer(portStr);
                                } catch (NumberFormatException e) {
                                        throw new IllegalArgumentException("Incorrect port");
                                }
                                if (!(_port > 0 && _port < 3000)) {
                                        throw new IllegalArgumentException("Value of port is out of range");
                                }
                        }
                } else if (_protocol == Protocol.HTTP) {
                        _port = 80;
                } else if (_protocol == Protocol.HTTPS) {
                        _port = 443;
                }
        }

        private void validateDocument() {
                String str = _url.substring(_url.indexOf("://") + 3);
                int pos = str.indexOf('/');
                if (pos == -1) {
                        _document = "/";
                } else {
                        String strDocument = str.substring(pos);
                        if (strDocument.contains("//")) {
                                throw new IllegalArgumentException("Incorrect document");
                        } else {
                                _document =  strDocument;
                        }
                }
        }

        public String getURL() {
                return _url;
        }


        public String getDomain() {
                return _domain;
        }

        public int getPort() {
                return _port;
        }

        public String getDocument() {
                return _document;
        }

        public String getStringFromProtocol() {
                if (_protocol == Protocol.HTTP) {
                        return "http";
                } else {
                        return "https";
                }
        }
}
