#!/usr/local/bin/env groovy
def ecrCreateRepo(docker_repo, region = "us-east-1") {
    // check if the repo exists
    def statusCode = sh(
      script: 'aws ecr describe-repositories --repository-name ' + docker_repo + ' >/dev/null 2>&1 || echo $?',
      returnStdout: true).trim()
    if (statusCode == "255") {
      // repo not found, so create it
      sh "aws ecr create-repository --repository-name '${docker_repo}' --region '${region}'"
    } else if (statusCode != "") {
      // some other error, so we should see what it is
      sh "aws ecr describe-repositories --repository-name '${docker_repo}'"
    }
}
def ecrCreateRepo2(docker_repo, region = "us-east-1", profile = "xcijv_us_dev_qa") {
    // check if the repo exists
    def statusCode = sh(
      script: 'aws ecr describe-repositories --repository-name ' + docker_repo + ' >/dev/null 2>&1 || echo $?',
      returnStdout: true).trim()
    if (statusCode == "255") {
      // repo not found, so create it
      sh "aws --profile '${profile}' ecr create-repository --repository-name '${docker_repo}' --region '${region}'"
    } else if (statusCode != "") {
      // some other error, so we should see what it is
      sh "aws ecr describe-repositories --repository-name '${docker_repo}'"
    }
}

def ecrLogin(region ="us-east-1") {
    sh("aws ecr get-login --region ${region} --no-include-email > ecrlogin")
    def command = readFile('ecrlogin').trim()
    sh("${command}")
}
def ecrLogin2(region ="us-east-1", profile = "xcijv_us_dev_qa") {
    sh("aws ecr get-login --region ${region} --profile ${profile} --no-include-email > ecrlogin")
    def command = readFile('ecrlogin').trim()
    sh("${command}")
}
def ecrPush(docker_repo, service_name, version = "latest") {
  sh "docker push ${docker_repo}/${service_name}:${version}"
}
def updateKubeConfig(region, cluster) {
  sh "aws eks --region ${region} update-kubeconfig --name ${cluster}"
}
def updateKubeConfig2(region, cluster, profile = "xcijv_us_dev_qa") {
  sh "aws eks --profile ${profile} --region ${region} update-kubeconfig --name ${cluster}"
}
def kubectlApply(yamlfile) {
  sh "kubectl apply -f ${yamlfile}"
}
