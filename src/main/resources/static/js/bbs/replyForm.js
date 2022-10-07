'use strict';
//textArea => ck_editor 대체
createCkEditor(document.querySelector( '#bcontent' ),false);
const $bbs = document.querySelector('.bbs-wrap');
const category = ($bbs?.dataset.code)? $bbs.dataset.code : '';

//등록
const $writeBtn = document.getElementById('writeBtn');
$writeBtn?.addEventListener("click", e=>{
  replyForm.submit();
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener("click",e=>{
  const url = `/bbs/list?category=${category}`;
  location.href = url;
});