#!/usr/local/bin/env groovy
def sendNotification(){
  sh "echo To be implemented"
}
def sedYaml(yamlfile, env_yaml, deployment_image, environment){
  // copy yaml from Git so we can sed on it
  sh "cp ${yamlfile} ${env_yaml}"
  // replace image name with sed on new yaml for this env
  sh "sed -i 's/{{ORIGINAL_IMAGE}}/${deployment_image}/g' ${env_yaml}"
  sh "sed -i 's/{{ENVIRONMENT}}/${environment}/g' ${env_yaml}"
}
