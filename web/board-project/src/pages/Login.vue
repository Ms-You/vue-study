<template>
  <div :class="['container', { 'right-panel-active': isActive }]" id="container">
    <div class="form-container sign-up-container">
      <div class="sign-form">
        <h1>Create Account</h1>
        <span>type your email for registration</span>
        <div class="email-container">
          <input type="email" class="email" placeholder="Email" v-model="registerState.form.email" />
          <button class="duplicate-check" @click="emailCheck()">중복 확인</button>
        </div>
        <small v-if="registerState.message" :class="{'text-available': registerState.messageType === 'available', 'text-unusable': registerState.messageType === 'unusable'}">{{ registerState.message }}</small>
        <input type="text" class="nickName" placeholder="Nickname" v-model="registerState.form.nickName" />
        <input type="password" class="password" placeholder="Password" v-model="registerState.form.password" />
        <input type="password" class="password-confirm" placeholder="Confirm your password" v-model="registerState.form.passwordConfirm" />
        <small v-if="!isCorrect" class="text-unusable">비밀번호가 일치하지 않습니다.</small>
        <button @click="register()">Sign Up</button>
      </div>
    </div>
    <div class="form-container sign-in-container">
      <div class="sign-form">
        <h1>Sign in</h1>
        <span>use your account</span>
        <input type="email" placeholder="Email" @keyup.enter="login()" v-model="loginState.form.email" />
        <input type="password" placeholder="Password" @keyup.enter="login()" v-model="loginState.form.password" />
        <a href="#">Forgot your password?</a>
        <button @click="login()">Sign In</button>
      </div>
    </div>
    <div class="overlay-container">
      <div class="overlay">
        <div class="overlay-panel overlay-left">
          <h1>Welcome Back!</h1>
          <p>To keep connected with us please login with your personal info</p>
          <button class="ghost" id="signIn" @click="activePanel(false)">Sign In</button>
        </div>
        <div class="overlay-panel overlay-right">
          <h1>Hello, Friend!</h1>
          <p>Enter your personal details and use our service</p>
          <button class="ghost" id="signUp" @click="activePanel(true)">Sign Up</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import { reactive, ref } from 'vue'
import router from '../scripts/router';
import store from '../scripts/store'

export default {
  setup() {
    const isActive = ref(false);
    const isCorrect = ref(true);

    /**
     * 로그인, 회원가입 좌우 변경 이벤트
     * @param status 
     */
    const activePanel = (status) => {
      isActive.value = status;
    };

    /**
     * 비밀번호 일치 여부 확인
     * @param status 
     */
    const correctPassword = (status) => {
      isCorrect.value = status;
    }

    /**
     * 회원가입을 위한 양식
     */
    const registerState = reactive({
      form: {
        email: "",
        nickName: "",
        password: "",
        passwordConfirm: "",
      },
      message: "",
      messageType: ""
    });

    /**
     * 로그인을 위한 양식
     */
    const loginState = reactive({
      form: {
        email: "",
        password: "",
      }
    });

    /**
     * 이메일 중복 체크
     */
    const emailCheck = () => {
      axios.get(`/auth/check-email?email=${registerState.form.email}`).then((res) => {
        console.log(res.data);
        registerState.message = res.data.message;
        registerState.messageType = 'available';
      }).catch((error) => {
        if(error.response && error.response.data) {
          registerState.message = error.response.data.message;
          registerState.messageType = 'unusable';
        } else {
          registerState.message = '사용할 수 없는 이메일입니다.';
          registerState.messageType = 'unusable';
        }
      });
    };

    /**
     * 회원가입 api 호출
     */
    const register = () => {
      correctPassword(true);
      const isFormFilled = registerState.form.email && registerState.form.nickName 
      && registerState.form.password && registerState.form.passwordConfirm;

      if(!isFormFilled) {
        window.alert('모든 필드를 채워주세요.')
        return;
      }

      if(registerState.form.password !== registerState.form.passwordConfirm) {
        correctPassword(false);
        return;
      } else {
        axios.post('auth', registerState.form).then((res) => {
          correctPassword(true);
          window.alert('회원가입이 완료되었습니다.');
          registerState.messageType = "";
          registerState.message = "";
          activePanel(false);
        }).catch((error) => {
          if(error.response && error.response.data) {
            registerState.message = error.response.data.message;
            registerState.messageType = 'unusable';
          } else {
            registerState.message = '사용할 수 없는 이메일입니다.';
            registerState.messageType = 'unusable';
          }
        })
      }
    };

    /**
     * 로그인 api 호출
     */
    const login = () => {
      axios.post('/auth/login', loginState.form).then((res) => {
        const accessToken = res.headers["authorization"];

        if(accessToken) {
          localStorage.setItem('accessToken', accessToken);
          window.alert('로그인 성공!');
          store.dispatch('initializeAuthentication');
          router.push({path: '/'});
        } else {
          window.alert('로그인 토큰을 받아오는데 실패했습니다.');
        }
      }).catch(() => {
        window.alert('아이디 혹은 비밀번호가 일치하지 않습니다..');
      })
    }

    return {
      registerState,
      loginState,
      isActive,
      activePanel,
      isCorrect,
      correctPassword,
      emailCheck,
      register,
      login
    }
  }
}

</script>
<style scoped>
  @import '../assets/login.css';
</style>
