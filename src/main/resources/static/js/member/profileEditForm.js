'use strict';
const $root = document.getElementById('root');
changeImageBtn.addEventListener('click',e=>attach.click());
deleteImageBtn.addEventListener('click',deleteImage_f);

//첨부파일 변경시
attach.addEventListener('change',e=> attach_f(e.target.files));

pic.addEventListener('dragover',e=>{
  e.stopPropagation();e.preventDefault()
});

//이미지 드롭시
pic.addEventListener('drop',e=>{
  e.stopPropagation();e.preventDefault();
  attach_f(e.dataTransfer.files);
});

//별칭변경
changeNickBtn.addEventListener('click',e=>{
  const url = `/api/members/${$root.dataset.sMemberId}/pfnick`;
  fetch(url, {
    method:'PUT',
    'content-type':'application/txt',
    body:nickname.value
  }).then(res=>res.json())
    .then(res=>{
        console.log(res);
        if(res.rtcd === '00'){
          location.reload();
        }
    });
});

//이미지 미리보기
function attach_f(files) {
  if (!files.length) {
    profileMsg.innerHTML = "<p>파일을 첨부하세요</p>";
    return false;
  }
  if (files.length > 1) {
    profileMsg.innerHTML = "<p>파일 1개만 첨부하세요</p>";
    return false;
  }

  const file = files[0];

  if(!file.type.match('image/')) {
    profileMsg.innerHTML = "<p>이미지 파일을 첨부하세요</p>";
    return false;
  }

  //파일사이즈 체크
  if(file.size > 1024 * 1024 * 1) {
    alert('1M가 이하만 가능합니다.');
    return false;
  }

  profileMsg.innerHTML = "";
  pic.src = URL.createObjectURL(file);
  pic.style.height = '100%';
  pic.style.width = '100%';
  pic.addEventListener('load', e => {
    changeServerImg(file);
    URL.revokeObjectURL(e.target.src)
  });

}

//이미지 변경
function changeServerImg(file){
  const url = `/api/members/${$root.dataset.sMemberId}/pfimg`;
  fetch(url, {
    method:'PUT',
    'content-type':'application/octet-stream',
    body:file
  }).then(res=>res.json())
    .then(res=>{
       if(res.rtcd === '00'){
         location.reload();
       }
    });
}

//이미지 삭제
function deleteImage_f(e){
  changeServerImg(null);
  pic.src='/img/noprofile.jpg';
}