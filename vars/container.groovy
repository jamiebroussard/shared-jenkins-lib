#!/usr/local/bin/env groovy
def rmDanglingContainers() {
  sh 'docker ps -q -f status=exited | xargs --no-run-if-empty docker rm'
}
def rmDanglingImages() {
  sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
}
def rmDanglingVolumes() {
  sh 'docker volume ls -qf dangling=true | xargs -r docker volume rm'
}
def buildImage(docker_repo, service_name, docker_context, dockerfile = "Dockerfile", version = "latest") {
  sh "docker build --rm --pull --no-cache -t ${service_name} ${docker_context}"
  sh "docker tag ${service_name}:latest ${docker_repo}/${service_name}:${version}"
}
// docker tag static-asset-api-spring-boot:latest 087395885892.dkr.ecr.us-east-1.amazonaws.com/static-asset-api-spring-boot:latest
def dockerCleanUpRemote(docker_repo, service_name, pk_dev_connect) {
  sh "docker -H ${pk_dev_connect} stop ${service_name} || true && docker -H ${pk_dev_connect} rm ${service_name} || true"
}
def dockerPullRemote(docker_repo, service_name, pk_dev_connect) {
  sh "docker -H ${pk_dev_connect} pull ${docker_repo}/${service_name}"
}
def dockerRunRemote(docker_repo, service_name, pk_dev_connect, service_port){
  sh "docker -H ${pk_dev_connect} run --name ${service_name} -d -p ${service_port} ${docker_repo}/${service_name}:latest"
}
def deploy(yamlfile, type = 'k8') {
  if ( type == "k8") {
    sh "kubectl apply -f ${yamlfile}"
  }
}
def deploy_old_prod( yamlfile, type = 'k8' ) {
  if ( type == "k8" ) {
    // Deploy to dev account main region
    aws.updateKubeConfig2(project.dev_region, project.old_prod_cluster, project.dev_profile)
    sh "kubectl apply -f ${yamlfile}"
    // Deploy to DR region
  }
}
def deploy_prod( yamlfile, type = 'k8' ) {
  if ( type == "k8" ) {
    // Deploy to main region
    sh "export AWS_PROFILE=jdbroussard"
    sh "aws eks update-kubeconfig --region us-east-1 --name jdbroussard"
    //aws.updateKubeConfig2(project.produseast_region, project.prod_cluster, project.prod_profile)
    sh "kubectl apply -f ${yamlfile}"
    // Deploy to DR region
  }
}
def deploy_stage( yamlfile, type = 'k8' ) {
  if ( type == "k8" ) {
    // Deploy to main region
    aws.updateKubeConfig2(project.prod_region, project.stage_cluster, project.prod_profile)
    sh "kubectl apply -f ${yamlfile}"
    // Deploy to DR region
  }
}
def deploy_dev(yamlfile, type = 'k8') {
  if ( type == "k8") {
    // Deploy to main region
    aws.updateKubeConfig2(project.dev_region, project.dev_cluster, project.dev_profile)
    sh "kubectl apply -f ${yamlfile}"
  }
}
def deploy_qa(yamlfile, type = 'k8') {
  if ( type == "k8") {
    // Deploy to main region
    aws.updateKubeConfig2(project.dev_region, project.qa_cluster, project.dev_profile)
    sh "kubectl apply -f ${yamlfile}"
  }
}
def setImage(service_name, docker_repo, version) {
  sh "kubectl set image deployment/${service_name} ${service_name}=${docker_repo}/${service_name}:${version}"
}
def build_push(docker_url, service_name, version){
  docker.withRegistry("$docker_url") {
    app = docker.build("$service_name")
    app.push("${version}")
  }
}
