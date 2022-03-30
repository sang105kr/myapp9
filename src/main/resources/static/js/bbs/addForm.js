'use strict';
const $bbs = document.querySelector('.bbs-wrap');
const category = ($bbs?.dataset.code)? $bbs.dataset.code : '';

//등록
const $writeBtn = document.getElementById('writeBtn');
$writeBtn?.addEventListener("click", e=>{
  writeForm.submit();
});
//목록
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener("click",e=>{
  const url = `/bbs/list?category=${category}`;
  location.href = url;
});
//분류자동 선택
const $options = document.querySelectorAll('#bcategory option');
[...$options].find(option=>option.value===category).setAttribute('selected','selected');