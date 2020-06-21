#!/usr/local/bin/env groovy
def imageSecScan() {
  sh "echo SecScan To be implemented"
}
def unitTest() {
  sh "echo Unit Test To be implemented"
}
def integrationTest() {
  sh "echo Integration To be implemented"
}
def regresionTest(){
  sh "echo Regression To be implemented"
}
def codeQualityScan() {
  sh "echo Code Qaulity Scan To be implemented"
}
def codeSecScan() {
  sh "echo Code Sec Scan To be implemented"
}
def devSanityTest() {
  sh "kubectl get pods"
}
def prodSanityTest() {
  sh "kubectl get pods"
}
