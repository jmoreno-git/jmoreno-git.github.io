# Arquitectures model-vista-controlador (MVC)

## Objectius

* Prendre consciència de la importància de l'ús de patrons de disseny d'aplicacions per a la solució de problemes coneguts.
* Valorar l'ús d'arquitectures sòlides per al disseny d'aplicacions per tal de facilitar-ne el manteniment i garantir-se l'escalabilitat.
* Implementar l'arquitectura Model-Vista-Controlador en una aplicació senzilla amb un únic controlador.

## Seqüenciació

* Els patrons i les arquitectures.
* Arquitectures de dues i de tres capes.
* Client-servidor versus Model-vista-controlador.
* Estructura MVC. Variants principals.
* Opcions d'implementació.
* Exemple complet.

## Importància de l'ús de patrons

[Frank Sinatra - My Way](https://www.youtube.com/watch?t=51&v=yGwwkiWWqIk&feature=youtu.be)

[Patrons de disseny de programari](https://en.wikipedia.org/wiki/Software_design_pattern)

* Estandardització
* Ús de solucions solvents i provades.
* Disseny obert a la ampliació i tancat a les modificacions.
* Seguretat del testeig i minimització dels canvis en la resta de mòduls de l'aplicació.
* Millora en  la rapidesa de desenvolupament.

**Develop not just your way, but the best way!**

![cuando_reviso_codigo_6_meses-despues.png](/damm06/assets/0.1/cuando_reviso_codigo_6_meses-despues.png)

## Arquitectures: comparació client-servidor i MVC

[Arquitectura client servidor](https://en.wikipedia.org/wiki/Client%E2%80%93server_model)

[Arquitectura MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)

## Com implementarem MVC?

Comparació entre les arquitectures Model-View-Controller i Model-View-Presenter

![mvc_mvp.png](/damm06/assets/0.1/mvc_mvp.png)

La variant d'arquitectura MVC que implementarem és la Model-View-Presenter.

![model_view_presenter_gui_design_pattern.png](/damm06/assets/0.1/model_view_presenter_gui_design_pattern.png)

El model i la vista no es comuniquen ni es coneixen de cap manera. Totes les comunicacions passen pel controlador (*presenter*).

La vista pot ser totalment passiva, encarregant-se el controlador de la gestió dels esdeveniments.

Per al cas d'aplicacions web, i molt especialment per a les aplicacions web híbrides, l'aplicació és distribuïda entre servidor i client. Cada part pot implementar una versió de l'arquitectura MVC.

Possibles implementacions:

* Cada vista té un únic controlador associat que gestiona els seus esdeveniments i envia peticions al model
* Un controlador únic centralitza totes les peticions de totes les vistes sobre el model. Pot gestionar també els esdeveniments de les vistes (ha de tenir accés a totes) o deixar que altres controladors gestionin els esdeveniments de les vistes

Comunicació amb el model:

* El controlador(o els controladors si s'hi ha més d'un) envia peticions (executa metodes) del model i aquests retornen valors al controlador, el qual actualitza les vistes
* El controlador envia les peticions al model i aquest no retorna dades, sinó que actualitza el seu estat, genera un esdeveniment de canvi d'estat i l'envia als observadors. Com a observadors del model poden estar les vistes o els controladors.

Exemple senzil de l'arquitectura Model-Vista-Controlador (MVC). [mvc-simple_example.zip](/damm06/assets/0.1/mvc-simple_example.zip)
