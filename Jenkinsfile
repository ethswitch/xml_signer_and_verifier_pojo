node {
      def image
      def WORKSPACE ="/var/jenkins_home/workspace/xml_signer_build_file"
    def dockerImageTag ="xmlSigner${env.BUILD_NUMBER}"

 stage("Clone project") {
    git credentialsId: 'jenkins_authentication', branch: 'xml_digest_prod', url: 'https://github.com/tttesfaye/xmlSigner.git'
  }


  stage("Build project with test execution") {
    sh " cd /opt/jenkins/home/workspace/xml_signer_build"
    sh " chmod +x /opt/jenkins/home/workspace/xml_signer_build/gradlew  /opt/jenkins/home/workspace/xml_signer_build"

       sh "./gradlew build -x test --scan -s"
  }
  stage("create -- image"){

     image = docker.build('ethswitch/xmlsigneramh:v1.0.0', ".")

  }
stage('Push') {
// // Login to Docker Hub using the credentials stored in Jenkins
  docker.withRegistry('https://registry.hub.docker.com', 'docker_authentication') {
// // Push the image to the repository
  image.push()
}
   }
  stage("deploy"){
   sh "docker compose down"
  sh "docker compose up -d"
  }
}