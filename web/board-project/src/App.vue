<template>
  <Header />
  <RouterView />
  <Footer />
</template>

<script>
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import { RouterView } from 'vue-router'
import store from './scripts/store';

export default {
  name: 'App',
  components: {
    Header,
    Footer,
  },
  setup() {
    const id = sessionStorage.getItem("id");

    if(id) {
      store.commit('setAccount', id);
    }
  }
}
</script>

<style scoped>
    @import './assets/base.css'
</style>
