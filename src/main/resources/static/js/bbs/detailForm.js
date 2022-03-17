'use strict';
//답글
replyBtn.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}/reply`;
  location.href = url;
});
//수정
editBtn.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}/edit`;
  location.href = url;
});
//삭제
delBtn.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}/del`;
  location.href = url;
});
//목록
listBtn.addEventListener('click',e=>{
  location.href="/bbs";
});