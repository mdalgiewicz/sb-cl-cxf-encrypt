## Keystore i Trust store oddzielnie
### Utworzenie klucza i certyfikatu serwera
keytool -genkeypair -alias serverkey -keyalg RSA -keypass password -keystore server-keystore.jks -storepass password
### Utworzenie klucza i certyfikatu klienta
keytool -genkeypair -alias clientkey -keyalg RSA -keypass password -keystore client-keystore.jks -storepass password
### Export certyfikatu serwera do pliku
keytool -exportcert -alias serverkey -file server-public.cer -keystore server-keystore.jks -storepass password
### Export certyfikatu klienta do pliku
keytool -exportcert -alias clientkey -file client-public.cer -keystore client-keystore.jks -storepass password
### Import certyfikatu serwera do oddzielnego keystore (truststore dla klienta)
keytool -importcert -keystore client-truststore.jks -alias servercert -file server-public.cer -storepass password -noprompt
### Import certyfikatu klienta do oddzielnego keystore (truststore dla serwera)
keytool -importcert -keystore server-truststore.jks -alias clientcert -file client-public.cer -storepass password -noprompt

