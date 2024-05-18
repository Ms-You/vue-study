<template>
  <div :class="['container', { 'right-panel-active': isActive }]" id="container">
    <div class="form-container sign-up-container">
      <div class="sign-form">
        <h1>Create Account</h1>
        <span>use your email for registration</span>
        <input type="text" placeholder="Nickname" />
        <input type="email" placeholder="Email" />
        <input type="password" placeholder="Password" />
        <button>Sign Up</button>
      </div>
    </div>
    <div class="form-container sign-in-container">
      <div class="sign-form">
        <h1>Sign in</h1>
        <span>use your account</span>
        <input type="email" placeholder="Email" @keyup.enter="submit()" v-model="state.form.email" />
        <input type="password" placeholder="Password" @keyup.enter="submit()" v-model="state.form.password" />
        <a href="#">Forgot your password?</a>
        <button @click="submit()">Sign In</button>
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
          <p>Enter your personal details and use gallery</p>
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
    const state = reactive({
      form: {
        email: "",
        password: "",
      }
    })

    const isActive=  ref(false);

    /**
     * 로그인, 회원가입 좌우 변경 이벤트
     * @param status 
     */
    const activePanel = (status) => {
      isActive.value = status;
    }

    const submit = () => {
      axios.post('/auth/login', state.form).then((res) => {
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
      state,
      isActive,
      activePanel,
      submit
    }
  }
}

</script>
<style scoped>
  @import '../assets/login.css';
</style>
