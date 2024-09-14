function submitComment(){
  if(!formValidate('commentForm')){
    return; 
  }
  createPostRequest('commentForm','/comment/create','createCommentCB');

}

function createCommentCB(r){
  console.log(r);
}

function submitPublisher(){
  if(!formValidate('publisherForm')){
    return; 
  }
  createPostRequest('publisherForm','/publisher/create','submitPublisherCB');

}

function submitPublisherCB(r){
  console.log(r);
}