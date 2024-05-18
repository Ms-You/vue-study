<template>
  <header class="p-3 text-bg-dark">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <div class="navbar navbar-dark bg-dark shadow-sm">
          <div class="container">
            <router-link to="/" class="navbar-brand d-flex align-items-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24">
                <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/>
                <circle cx="12" cy="13" r="4"/></svg>
              <strong>Gallery</strong>
            </router-link>
          </div>
        </div>

        <form class="search col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
          <input type="search" class="form-control form-control-dark text-bg-dark" placeholder="Search..." aria-label="Search">
        </form>

        <div class="text-end">
          <router-link to="/login" class="btn btn-outline-light me-2" v-if="!isAuthenticated">Login</router-link>
        </div>

        <div class="dropdown text-end" v-if="isAuthenticated">
          <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
            <img src="" alt="프로필 이미지" width="32" height="32" class="rounded-circle">
          </a>
          <ul class="dropdown-menu text-small">
            <li>
              <a class="dropdown-item" href="#">Profile
                <i class="bx bxs-face"></i>
              </a>
            </li>
            <li>
              <router-link to="/orders" class="dropdown-item">Orders
                <i class="fa fa-shopping-bag" aria-hidden="true"></i>
              </router-link>
            </li>
            <li>
              <router-link to="/cart" class="dropdown-item">Cart
                <i class="fa fa-shopping-cart" aria-hidden="true"></i>
              </router-link>
            </li>
            <li><hr class="dropdown-divider"></li>
            <li>
              <a to="/login" class="dropdown-item" @click="logout">Sign out
                <i class="bx bxs-log-out-circle"></i>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import store from '../scripts/store'
import router from '../scripts/router'
import axios from 'axios'
import { computed } from 'vue'

export default {
  name: 'Header',
  setup() {
    const isAuthenticated = computed(() => store.state.isAuthenticated);

    const logout = () => {
      axios.post('/auth/logout').then(() => {
        store.dispatch('logout');
        router.push({path: "/"});
      });
    }

    return {
      isAuthenticated,
      logout
    }
  }
}
</script>

<style scoped>
header ul li a {
  cursor: pointer;
}

header .cart {
  margin-left: auto;
  color: #fff
}

.search input[type="search"] {
  width: 25%;
  margin-left: auto;
}

.search {
  flex-grow: 1;
}
</style>