'use strict';
//상세
const $cancelBtn = document.getElementById('cancelBtn');
$cancelBtn?.addEventListener('click',e=>{
  const url = `/bbs/${bbsId.value}`;
  location.href = url;
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener('click',e=>{
  location.href="/bbs";
});