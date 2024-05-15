<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(i, idx) in state.items" :key="idx">
          <img :src="i.imgPath" />
          <span class="name">{{ i.name }}</span>
          <span class="price">{{ lib.getNumberFormat(i.price - i.price * i.discountRate / 100) }}원</span>
          <i class="fa fa-trash" @click="removeItem(i.id)"></i>
        </li>
      </ul>
      <router-link to="/order" class="btn btn-primary">주문하기</router-link>
    </div>
  </div>
</template>
<script>
import { reactive } from 'vue';
import axios from '../axios'
import lib from '../scripts/lib'

export default {
  setup() {
    const state = reactive({
      items: []
    })

    const loadItem = () => {
      axios.get('/member/cart/item').then((res) => {
        console.log(res.data);
        state.items = res.data.result;
      })
    }

    const removeItem = (itemId) => {
      axios.delete(`/member/cart/item/${itemId}`).then(() => {
        loadItem();
      })
    }

    loadItem();

    return {
      state,
      lib,
      loadItem,
      removeItem,
    }
  }
}
</script>
<style scoped>
.cart ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.cart ul li {
  border: 1px solid #eee;
  margin-top: 25px;
  margin-bottom: 25px;
}

.cart ul li img {
  width: 150px;
  height: 150px;
}

.cart ul li .name {
  margin-left: 25px;
}

.cart ul li .price {
  margin-left: 25px;
}

.cart ul li i {
  float: right;
  font-size: 20px;
  margin-top: 65px;
  margin-right: 50px;
}

.cart .btn {
  width: 150px;
  display: block;
  padding: 15px 30px;
  font-size: 20px;
  margin: 0 auto;
  margin-bottom: 30px;
}
</style>
