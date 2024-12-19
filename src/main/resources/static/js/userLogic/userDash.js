var user={},
blogDict={},
blogCount=$('#blogCount');

$(function(){
  loadDashboard();
});
function loadDashboard(){
  // console.log('user',loadedDTA.user);
user=loadedUserDTA.user;
blogCount.html(loadedUserDTA.user.blog.length);
loadedUserDTA.user.blog.forEach(d => blogDict[d.id]=d);
}