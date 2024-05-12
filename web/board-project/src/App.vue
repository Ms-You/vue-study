<template>
  <Header />
  <Home />
  <Footer />
</template>

<script>
import Header from './components/Header.vue'
import Home from './pages/Home.vue'
import Footer from './components/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    Home,
    Footer,
  }
}
</script>

<style scoped>
    @import './assets/base.css'
</style>
