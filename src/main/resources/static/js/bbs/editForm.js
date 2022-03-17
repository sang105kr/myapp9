'use strict';
//상세
cancelBtn.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}`;
  location.href = url;
});
//목록
listBtn.addEventListener('click',e=>{
  location.href="/bbs";
});