# cadastroAPI
1- Partindo do pressuposto:

github instalado https://git-scm.com/downloads
Maven instalado https://maven.apache.org/download.cgi
java jdk instaldo https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html
IntelliJ IDEA instalado https://www.jetbrains.com/idea/download/?section=window

2- Criar um APPLICATION.PROPERTIES na pasta do resources -> META-INF :

*Dados do BD

spring.datasource.url = jdbc:mysql://localhost:3306/ NOME DO PROJETO DO BANCO DE DADOS ?serverTimezone=UTC

spring.datasource.username = USUARIO DO BD

spring.datasource.password = Senha do BD

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=false

spring.jpa.properties.hibernate.format_sql=true

spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false

spring.main.allow-bean-definition-overriding=true

spring.mail.host=smtp.office365.com

spring.mail.port=587

spring.mail.username=backoffice************

spring.mail.password=***********

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true

spring.mail.properties.mail.smtp.starttls.required=true
