# EBI-Genomics-Data

NOTE:
Please follow below instrcutions to setup and run this project.

-> Development Enviorment consists of:
	1. Sql server db.
	2. Java spring boot back-end.
	3. Angular 2 front-end.

# DATABASE SECTION

-> Pre-Requisites:
	1. SQL Server

-> For Database setup, execute following scripts in given order from folder named "scripts":
	1. create-tables.sql
	2. taxonomies.sql
	3. projects.sql

-> Create Table script will create [ebi-database] which will be connected with server-app.

-> Taxonomies and Projects will be created under [ebi-database].

# BACK-END SECTION

-> Pre-Requisites:
	1. Java 8 
	2. Spring Boot 1.5.8.RELEASE 
	3. Maven 3.3

-> Define Database name and SQL server credentials:
	1. Go to: server-app/src/main/resources/application.properties
	2. Replace '?' with database name, sql server authentication username and password.
		-> spring.datasource.url=jdbc:sqlserver://localhost;databaseName=?
		spring.datasource.username=?
		spring.datasource.password=?

-> Run SERVER-APP:
	1. Open terminal and go to "server-app" root folder.
	2. Run command: java -jar target/server-app-0.0.1-SNAPSHOT.jar
	3. If .jar is not in targer folder, go to "server-app" root to create .jar with:
		-> Run command: mvn clean package 
		-> Go to step 2.
	4. Application will listen on port 8080.

# FRONT-END SECTION

-> Pre-Requisites:
	1. @angular/cli: 1.2.4
	2. node: 7.9.0
	3. npm 5.0.2
	4. bower 1.8.0

-> Install required npm and bower packages: (if working on fresh copy)
	1. Open terminal and go to "client-app" root folder.
	2. Run command: npm install
	3. Run command: bower install

-> Run SERVER-APP:
	1. Open terminal and go to "client-app" root folder.
	2. Run command: ng serve
	3. Application will listen on port 4200.

# RUN-APPLICATION

1- Open browser and visit:
	-> http://localhost:4200

2. Following parts are covered in application:
	-> Taxonomy List Page: Taxonomy List, CRUD opertions on Taxonomy, pagination.
	-> Project List Page: Project List, CRUD opertions on Project, filter (By Taxonomy ID & Study Type) and pagination.
	-> Project Details Page: View, add, edit project.
	
# END
