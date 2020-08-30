package com.example.demo.services;

public class OauthService {
    
    private String clientId;
    private String clientSecret;
    private String callbackUrl;                             // redirect_uri
    private String responseType = "code";
    private String scope = "profile";                        // openid, profile, email

    private String authorizationEndpoint = "https://accounts.google.com/o/oauth2/v2/auth";
    private String tokenEndpoint = "https://oauth2.googleapis.com/token";

    public void oauthAuthorization(){

    }
    public void oauthToken(){

    }
}


// https://developers.google.com/identity/protocols/oauth2/openid-connect

// scope=openid%20email

// state // optional


/* 
// HTTPS GET

https://accounts.google.com/o/oauth2/v2/auth?
 response_type=code&
 client_id=424911365001.apps.googleusercontent.com&
 scope=openid%20email&
 redirect_uri=https%3A//oauth2.example.com/code&
 state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-demo.example.com%2FmyHome&
 login_hint=jsmith@example.com&
 nonce=0394852-3190485-2490358&
 hd=example.com
*/


/*
response callback redirect:

https://oauth2.example.com/code?
state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foa2cb.example.com%2FmyHome&
code=4/P7q7W91a-oMsCeLvIaQm6bTrgtp7&
scope=openid%20email%20https://www.googleapis.com/auth/userinfo.email


// Exchange code for access token and ID token
 
// https://oauth2.googleapis.com/token

POST /token HTTP/1.1
Host: oauth2.googleapis.com
Content-Type: application/x-www-form-urlencoded

code=4/P7q7W91a-oMsCeLvIaQm6bTrgtp7&
client_id=your-client-id&
client_secret=your-client-secret&
redirect_uri=https%3A//oauth2.example.com/code&
grant_type=authorization_code


// response
access_token	A token that can be sent to a Google API.
expires_in	The remaining lifetime of the access token in seconds.
id_token	A JWT that contains identity information about the user that is digitally signed by Google.
scope	The scopes of access granted by the access_token expressed as a list of space-delimited, case-sensitive strings.
token_type	Identifies the type of token returned. At this time, this field always has the value Bearer.
refresh_token

*/


// googleapis/google-api-java-client
// https://googleapis.github.io/google-api-java-client/oauth-2.0.html