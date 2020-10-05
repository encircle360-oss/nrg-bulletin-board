FROM openjdk:14
ADD build/libs/*.jar /nrg-bb.jar
CMD ["java","-jar","/nrg-bb.jar"]