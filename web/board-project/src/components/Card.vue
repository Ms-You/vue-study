<template>
  <div class="card shadow-sm">
    <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}" />
    <div class="card-body">
      <p class="card-text">
        <span>{{ item.name }} &nbsp;</span>
        <span class="discount badge bg-danger">
          {{ item.discountRate }}%
        </span>
      </p>

      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary" @click="addToCart(item.id)" v-if="isAuthenticated">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </button>
        <small class="price text-muted">
          {{ lib.getNumberFormat(item.price) }}원
        </small>
        <small class="real text-danger">
          {{ lib.getNumberFormat(item.price - (item.price * item.discountRate / 100)) }}원
        </small>
      </div>
    </div>
  </div>
</template>

<script>
import lib from '../scripts/lib'
import axios from '../axios'
import store from '../scripts/store'
import { computed } from 'vue'

  export default {
    name: 'Card',
    props: {
      item: Object
    },
    setup() {
      const isAuthenticated = computed(() => store.state.isAuthenticated);

      const addToCart = (itemId) => {
        axios.post(`/member/cart/item/${itemId}`).then(() => {
          console.log('success');
        })
      }

      return {
        lib,
        isAuthenticated,
        addToCart
      }
    }
  }
</script>

<style scoped>
.card .img {
  display: inline-block;
  width: 100%;
  height: 250px;
  background-size: cover;
  background-position: center;
}

.card .card-body .price {
  text-decoration: line-through;
}
</style>