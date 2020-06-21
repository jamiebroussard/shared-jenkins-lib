#!/usr/local/bin/env groovy
def emailext(email_to){
  emailext
    body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
    to: "${email_to}"
}
// TODO ADD recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
