# example how to invoke the server with additional JARs.
# In this case, we cannot use the -jar option, because the Manifest then overrides any additional classpath parameters

java -javaagent:/home/mbi/java/eclipselink/jlib/eclipselink.jar \
    -cp ../t9t-demo-api/target/t9t-demo-api-2.5.0-SNAPSHOT.jar:../t9t-demo-be/target/t9t-demo-be-2.5.0-SNAPSHOT.jar:target/t9t-server.jar \
    com.arvatosystems.t9t.server.Main -T ACME -U mbi
