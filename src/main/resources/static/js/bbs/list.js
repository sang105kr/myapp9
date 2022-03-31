'use strict';
const $bbs = document.querySelector('.bbs-wrap');
const category = ($bbs?.dataset.code)? $bbs.dataset.code : '';

const $writeBtn = document.getElementById('writeBtn');
$writeBtn?.addEventListener("click", e=>{
  const url = `/bbs/add?category=${category}`
  location.href= url;   // get /bbs/add
});


//검색
const $searchType = document.getElementById('searchType');
const $keyword    = document.getElementById('keyword');
const $searchBtn  = document.getElementById('searchBtn');

//검색 버튼 클릭시
$searchBtn?.addEventListener('click', search_f);

//키워드 입력필드에 엔터키 눌렀을때 검색
$keyword?.addEventListener('keydown', e=>{
    if(e.key === 'Enter') {
        search_f(e);
    }
});

function search_f(e){
    //검색어입력 유무체크
    if($keyword.value.trim().length === 0){
       alert('검색어를 입력하세요');
       $keyword.focus();$keyword.select(); //커서이동
       return false;
    }
    const url = `/bbs/list/1/${$searchType.value}/${$keyword.value}?category=${category}`;
    location.href = url;
}




