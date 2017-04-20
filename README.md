# SpringBoot, Contract Last, CXF Client & Server with WSS Encrypt

### About

This projects demonstrates how to configure ws security encryption with Apache CXF on client and server side. Both projects providing support
for wss encrypt to incoming and outgoing request.

* [sb-cl-cxf-wss-encrypt-server](https://github.com/dalgim/sb-cl-cxf-encrypt/tree/master/sb-cl-cxf-wss-encrypt-server) - Server side
* [sb-cl-cxf-wss-encrypt-client](https://github.com/dalgim/sb-cl-cxf-encrypt/tree/master/sb-cl-cxf-wss-encrypt-client) - Client side

More helpful info you can find on this websites:
* Apache CXF WS Security introduction - [Link](http://cxf.apache.org/docs/ws-security.html)
* WS Security in CXF - IBM - [Link](https://www.ibm.com/developerworks/library/j-jws13/)
* JBossWS - [Link](https://developer.jboss.org/wiki/Jbossws-stackcxfUserGuide#jive_content_id_WSSecurity)
* Advanced User Guide JBoss Community - [Link](https://docs.jboss.org/author/display/JBWS/Advanced+User+Guide)


### Signature basic parameters
Below parameters are using in client and server configuration to get wss signature connection
<br/><b>ACTION</b> - One on supported security actions (e.g. Encrypt, Signature, UsernameToken, Timestamp)
<br/><b>ENC_PROP_FILE</b> - Encryption property file (keystore and truststore configuration) - for outgoing messages
<br/><b>DEC_PROP_FILE</b> - Decryption property file (keystore and truststore configuration) - for incoming messages
<br/><b>ENCRYPTION_USER</b> - Alias of server/client cert which will be use for encrypt/decrypt message
<br/><b>MUST_UNDERSTAND</b> - True if client/server must process ws security actions otherwise false
<br/><b>PW_CALLBACK_CLASS</b> - Name of PasswordCallback interface implementation which provides password for client/server key 

More about cxf configuration - [Link](https://ws.apache.org/wss4j/config.html)

### Creating server and client keys

Helpful websites:
* Oracle Keytool docs - [Link](http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/keytool.html)
* The Most Common Java Keytool Keystore Commands
[Link](https://www.sslshopper.com/article-most-common-java-keytool-keystore-commands.html) 

## Createing keys and certs
Generating server key and certificate
```sh
keytool -genkeypair -alias serverkey -keyalg RSA -keypass password -keystore server-keystore.jks -storepass password
```
Generating client key and certificate
```sh
keytool -genkeypair -alias clientkey -keyalg RSA -keypass password -keystore client-keystore.jks -storepass password
```
Export server certificate into file
```sh
keytool -exportcert -alias serverkey -file server-public.cer -keystore server-keystore.jks -storepass password
```
Export client certificate into file
```sh
keytool -exportcert -alias clientkey -file client-public.cer -keystore client-keystore.jks -storepass password
```
Import server certificate into client truststore
```sh
keytool -importcert -keystore client-truststore.jks -alias servercert -file server-public.cer -storepass password -noprompt
```
Import client certificate into client truststore
```sh
keytool -importcert -keystore server-truststore.jks -alias clientcert -file client-public.cer -storepass password -noprompt
```


### Running and testing

If you have any problems with your implementation you can enable precise exception message using below properties. It is very helpful option because in default cxf hides exception messages for security reason. 

```java
endpoint.getProperties().put(Message.EXCEPTION_MESSAGE_CAUSE_ENABLED, "true");
endpoint.getProperties().put(Message.FAULT_STACKTRACE_ENABLED, "true");
```
  

//TODO
