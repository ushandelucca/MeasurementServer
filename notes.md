# Notes
 
## Web Client
https://github.com/rajasegar/JADE-Bootstrap

http://intercoolerjs.org
https://github.com/jakerella/jquery-mockjax

## [Spark](http://sparkjava.com/)
http://www.mscharhag.com/java/building-rest-api-with-spark
https://github.com/mscharhag/blog-examples/tree/master/sparkdemo/src

https://github.com/fabiomaffioletti/jsondoc-samples/blob/master/jsondoc-sample-spark/src/main/java/org/jsondoc/sample/spark/controller/CityController.java
https://srlk.github.io/posts/2016/swagger_sparkjava/

# git Commands
Accidentally committed .idea directory files into git
http://stackoverflow.com/questions/11124053/accidentally-committed-idea-directory-files-into-git

    $ echo '.idea' >> .gitignore  
    $ git rm -r --cached .idea
    $ git add .gitignore
    $ git commit -m "removed .idea directory"
    $ git push

List files in local git repo?
http://stackoverflow.com/questions/8533202/list-files-in-local-git-repo

    $ git ls-tree --full-tree -r HEAD

Configure git to accept a particular self-signed server certificate for a particular https remote
http://stackoverflow.com/questions/9072376/configure-git-to-accept-a-particular-self-signed-server-certificate-for-a-partic

    $ git config --global http.sslCAInfo /home/javl/git-certs/cert.pem
    $ git config --global http.sslVerify false #NO NEED TO USE THIS
    $ // git config --global --unset http.sslVerify
    $ git config --global --list

Change the current branch to master in git
http://stackoverflow.com/questions/2763006/change-the-current-branch-to-master-in-git

    $ git checkout better_branch
    $ git merge --strategy=ours master    # keep the content of this branch, but record a merge
    $ git checkout master
    $ git merge better_branch             # fast-forward master up to the merge

