# Introducció a les tecnologies web

## Protocol HTTP. Arquitectura client-servidor 

[Història del www](https://es.wikipedia.org/wiki/Historia_de_la_World_Wide_Web)

**HTTP: Comunicació entre el client i el servidor amb PHP**

  - Teclegem a la barra del navegador l'adreça i l'arxiu a sol·licitar.
  - El navegador envia el missatge a través d'Internet a l'ordinador anomenada 'www.proven.cat' sol·licitant la pàgina (arxiu).
  - El servidor web rep el missatge i, en veure que l'extensió és "php", sol·licita a l'intèrpret de PHP que li enviï l'arxiu.
  - L'intèrpret PHP llegeix des del disc l'arxiu.
  - L'intèrpret PHP executa les ordres contingudes a l'arxiu i eventualment es comunica amb un gestor de base de dades (exemples d'ells poden ser MySql, Oracle, Informix, SQL Server, etc.)
  - Després d'executar el programa contingut a l'arxiu envia el resultat al servidor web en format HTML.
  - El servidor web envia la pàgina resultant al client que l'havia sol·licitat.
  - El navegador mostra en pantalla l'arxiu que va enviar al servidor web.

Llenguatges de programació web de servidor: php, jsp, asp, ...

Prerequisits de client: xhtml, html5, css.

## Hypertext Transfer Protocol (HTTP) 

* HTTP is an application layer protocol runs over TCP/IP. 
* The IP provides support for routing and addressing (via an unique IP address for machines connected to the Internet); 
* while TCP supports multiplexing via 64K ports from port number 0 to 65535. The default port number assigned to HTTP is TCP port 80. (Notes: TCP Port numbers below 1024 are reserved for popular protocols such as HTTP, FTP, SMTP; Port numbers 1024 and above could be used for applications.)
* HTTP is an asynchronous request-response application-layer protocol. A client sends a request message to the server. The server then returns a response message to the client. In other words, HTTP is a pull protocol, a client pulls a page from the server (instead of server pushes pages to the clients).
* The syntax of the message is defined for HTTP1.1 in the RFC7230-RFC7235 (2014), by IETF and W3C (before RFC2068 -1997, RFC2616-1999):

[Hypertext Transfer Protocol](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol)

![](assets/0.1/http_requestresponsemessages.png)

* HTTP/2 - almost same protocol but binary coded. Only could be used over HTTPS (TLS 1.2)

* HTTP/3 - over UPD (instead of TCP)


[Video "Cómo funciona una aplicación web"](https://www.youtube.com/watch?v=TR_H1qcff04&feature=youtu.be) por trisfera.com

