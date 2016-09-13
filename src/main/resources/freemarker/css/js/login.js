var myRef = new Firebase("https://incandescent-heat-8703.firebaseio.com");
var authClient = new FirebaseSimpleLogin(myRef, function (error, user) {
    if (error) {
        // an error occurred while attempting login
        console.log(error);
    } else if (user) {
        // user authenticated with Firebase
        console.log("User ID: " + user.uid + ", Provider: " + user.provider);
    } else {
        // user is logged out
    }
});

function userLogin() {
    console.log(authClient.login('password', {
        email: 'email',
        password: 'none'
    }));
}
