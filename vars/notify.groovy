#!/usr/local/bin/env groovy
def emailext(email_to){
  emailext
    body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
      <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
    subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    to: "${email_to}"
}

// TODO ADD recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
