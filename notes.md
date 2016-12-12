# Notes
 
## Web Client
- Switch to https://github.com/rajasegar/JADE-Bootstrap
- display the swagger.json on the website (nice formatted) and make it downloadable with [Javascript](http://stackoverflow.com/questions/4184944/javascript-download-data-to-file-from-content-within-the-page)
    - do not generate a temporary file
    - keep the route ```/apidoc/swagger```

Example for the static resources:
```bash
resources
+ - index.html  --> plaxceholder that redirects to website (jade)  
+ - swagger-ui
    + - swagger-ui files 
```

### More interesting things
- http://intercoolerjs.org
- https://github.com/jakerella/jquery-mockjax
- Badge champion: https://github.com/oshi/oshi/blob/master/README.md
- Next project: https://github.com/cgarwood/hacontrolpoint based on https://www.behance.net/gallery/9080423/HEIMA-Smart-Home-Automation-UI 

## [Spark](http://sparkjava.com/)
http://www.mscharhag.com/java/building-rest-api-with-spark
https://github.com/mscharhag/blog-examples/tree/master/sparkdemo/src

https://github.com/fabiomaffioletti/jsondoc-samples/blob/master/jsondoc-sample-spark/src/main/java/org/jsondoc/sample/spark/controller/CityController.java
https://srlk.github.io/posts/2016/swagger_sparkjava/

## git Commands
Accidentally committed .idea directory files into git
http://stackoverflow.com/questions/11124053/accidentally-committed-idea-directory-files-into-git
```bash
$ echo '.idea' >> .gitignore  
$ git rm -r --cached .idea
$ git add .gitignore
$ git commit -m "removed .idea directory"
$ git push
```
List files in local git repo?
http://stackoverflow.com/questions/8533202/list-files-in-local-git-repo
```bash
$ git ls-tree --full-tree -r HEAD
```
Configure git to accept a particular self-signed server certificate for a particular https remote
http://stackoverflow.com/questions/9072376/configure-git-to-accept-a-particular-self-signed-server-certificate-for-a-partic
```bash
$ git config --global http.sslCAInfo /home/javl/git-certs/cert.pem
$ git config --global http.sslVerify false #NO NEED TO USE THIS
$ // git config --global --unset http.sslVerify
$ git config --global --list
```
Change the current branch to master in git
http://stackoverflow.com/questions/2763006/change-the-current-branch-to-master-in-git
```bash
$ git checkout better_branch
$ git merge --strategy=ours master    # keep the content of this branch, but record a merge
$ git checkout master
$ git merge better_branch             # fast-forward master up to the merge
```
## Release workflow
Adapted from an [article](https://dzone.com/articles/why-i-never-use-maven-release) by Lieven Doclo - thank you very much!

1. Announce the release process and make sure that all the stuff is pushed to the development (dev) branch that needs to be included in the release.
2. Branch the development branch into a release branch. Following git-flow rules, I make a release branch 1.0.
3. Update the POM version of the development branch. Update the version to the next release version. For example mvn versions:set -DnewVersion=2.0-SNAPSHOT. Commit and push. Now, the work for the next release continues on the development branch.
4. Update the POM version of the release branch. Update the version to the standard CR version. For example mvn versions:set -DnewVersion=1.0.CR-SNAPSHOT. Commit and push.
5. Run tests on the release branch. Run all the tests. If one or more fail, fix them first.
6. Create a candidate release from the release branch.
    * Use the Maven version plugin to update your POM’s versions. For example mvn versions:set -DnewVersion=1.0.CR1. Commit and push.
    * Make a tag on git.
    * Use the Maven version plugin to update your POM’s versions back to the standard CR version. For example mvn versions:set -DnewVersion=1.0.CR-SNAPSHOT.
    * Commit and push.
    * Checkout the new tag.
    * Do a deployment build (mvn clean deploy). Since you’ve just run your tests and fixed any failing ones, this shouldn’t fail.
    * Put deployment on QA environment.
7. Iterate until QA gives a green light on the candidate release.
    1. Fix bugs. Fix bugs reported on the CR releases on the release branch. Merge into development branch on regular intervals (or even better, continuous). Run tests continuously, making bug reports on failures and fixing them as you go.
    2. Create a candidate release.
        * Use the Maven version plugin to update your POM’s versions. For example mvn versions:set -DnewVersion=1.0.CRx. Commit and push.
        * Make a tag on git.
        * Use the Maven version plugin to update your POM’s versions back to the standard CR version. For example mvn versions:set -DnewVersion=1.0.CR-SNAPSHOT.
        * Commit and push.
        * Checkout the new tag.
        * Do a deployment build (mvn clean deploy). Since you’ve run your tests continuously, this shouldn’t fail.
        * Put deployment on QA environment.
8. Once QA has signed off on the release, create a final release.
    * Check whether there are no new commits since the last release tag (if there are, slap developers as they have done stuff that wasn’t needed or asked for).
    * Use the Maven version plugin to update your POM’s versions. For example mvn versions:set -DnewVersion=1.0. Commit and push.
    * Tag the release branch.
    * Merge into the master branch.
    * Checkout the master branch.
    * Do a deployment build (mvn clean deploy).
    * Start production release and deployment process (in most companies, not a small feat). This can involve building the site and doing other stuff, some not even Maven related.