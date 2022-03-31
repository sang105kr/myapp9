'use strict';
//유효성 체크 상태
const validChkStatus = {
  email:false
}

const $email       = document.getElementById('email');
const $emailDupChk = document.getElementById('emailDupChk');

$emailDupChk.addEventListener('click', e=>{
  const $errmsg = $emailDupChk.closest('li').querySelector('.errmsg');
  if(!$email.value){
    $errmsg.textContent = '아이디를 입력하세요';
    $email.select();$email.focus();
    return false;
  }

  const xmlHttpreq = new XMLHttpRequest();

  const url = `/api/members/${$email.value}/exist`;
  xmlHttpreq.open("GET",url);
  xmlHttpreq.send();

  xmlHttpreq.addEventListener('load', e=>{
    if(xmlHttpreq.status ===200){ //성공적으로 서버응답 받으면
      console.log(xmlHttpreq.response);
      const result = JSON.parse(xmlHttpreq.response); //Json포맷 문자열 => JS객체로변환
      console.log(result);
      const $errmsg = $emailDupChk.closest('li').querySelector('.errmsg');

      if(result.rtcd === '00'){
        //alert('이미 사용되고 있는 아이디 입니다.');
        $errmsg.textContent = '이미 사용되고 있는 아이디 입니다.';
        $errmsg.style.display = 'block';

      }else{
        $errmsg.textContent = '';
        $errmsg.style.display = 'none';
        $emailDupChk.textContent = '사용가능';
        $emailDupChk.disabled = 'disabled';
        $email.readyOnly = 'readyOnly';
        validChkStatus.email = true;
      }
    }else{
      console.log('Error', xmlHttpreq.status, xmlHttpreq.statusText);
    }
  });
});

//회원가입 버튼 클릭시
joinBtn.addEventListener('click', e=>{

  //아이디 중복체크 미이행시
  if(!validChkStatus.email){
    alert('아이디 중복체크 바랍니다');
    $email.focus();
    $email.select();
    return;
  }

  joinForm.submit();
});
