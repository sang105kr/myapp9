'use strict';
createCkEditor(document.querySelector( '#bcontent' ),true);

const $bbs = document.querySelector('.bbs-wrap');
const category = ($bbs?.dataset.code)? $bbs.dataset.code : '';

//답글
const $replyBtn = document.getElementById('replyBtn');
$replyBtn?.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}/reply?category=${category}`;
  location.href = url;
});
//수정
const $editBtn = document.getElementById('editBtn');
$editBtn?.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}/edit?category=${category}`;
  location.href = url;
});
//삭제
const $delBtn = document.getElementById('delBtn');
$delBtn?.addEventListener('click',e=>{
  if(confirm('삭제하시겠습니까?')){
    const url = `/bbs/${bbsId.value}/del?category=${category}`;
    location.href = url;
  }
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener('click',e=>{
  const url = `/bbs/list?category=${category}`;
  console.log('url='+url);
  location.href=url;
});