#!/bin/bash

if [ x$1 = x ]; then
    password="HZgo89./jj#Rh"
else
    password=$1
fi
echo "Creating a new keystore with password " $password
rm t9tkeystore.jceks
keytool -genseckey -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg HMacSHA256 -keysize 2048 -alias HS256 -keypass $password
keytool -genseckey -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg HMacSHA384 -keysize 2048 -alias HS384 -keypass $password
keytool -genseckey -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg HMacSHA512 -keysize 2048 -alias HS512 -keypass $password
keytool -genkey -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg RSA -keysize 2048 -alias RS256 -keypass $password -sigalg SHA256withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkey -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg RSA -keysize 2048 -alias RS384 -keypass $password -sigalg SHA384withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkey -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg RSA -keysize 2048 -alias RS512 -keypass $password -sigalg SHA512withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkeypair -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg EC -keysize 256 -alias ES256 -keypass $password -sigalg SHA256withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkeypair -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg EC -keysize 256 -alias ES384 -keypass $password -sigalg SHA384withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
keytool -genkeypair -keystore t9tkeystore.jceks -storetype jceks -storepass $password -keyalg EC -keysize 256 -alias ES512 -keypass $password -sigalg SHA512withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 360
