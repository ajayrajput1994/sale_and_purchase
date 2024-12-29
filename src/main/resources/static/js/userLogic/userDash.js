var user={},
blogDict={}, 
blogList=[],
CommentDict={}, 
publisherDict={}, 
blogCount=$('#blogCount');

$(function(){
  loadDashboard();
});
function loadDashboard(){
  // console.log('user',loadedDTA.user);
user=loadedUserDTA.user;
blogCount.html(loadedUserDTA.user.blog.length);
loadedUserDTA.user.blog.forEach(d => {
  blogDict[d.id]=d;
  blogList.push(d);
});
loadedUserDTA.commets.forEach(d => {
  CommentDict[d.postid]=d;
});
loadedUserDTA.publishers.forEach(d => {
  publisherDict[d.postId]=d;
});
blogList = $.map(blogList, (item) => item).sort((a, b) => b.id - a.id);
// console.log(publisherDict);
renderLastFiveArticles();
renderArticlesChats();
}

function renderLastFiveArticles(){
  let h=[];
blogList.slice(0,5).forEach(d=>{
  // console.log(d);
  h.push(`<tr><td>${d.title}</td>`);
  h.push(`<td>${d.price}</td>`);
  h.push(`<td>${d.category}</td></tr>`);
})
$('#lastArticleBodyTable').html(h.join(''));
}

function renderArticlesChats(){
  let h=[],count=0;
  $.each(publisherDict,(postId,d)=>{
    if(blogDict.hasOwnProperty(postId)){
      // console.log(d);
      h.push(`<tr><td>${d.name}</td>`);
      h.push(`<td>${d.description}</td>`);
      h.push(`<td>${d.date}</td></tr>`);
      count+=1;
    }
  })

$('#chatcouont').html(count);
$('#articleChatTableBody').html(h.join(''));
}