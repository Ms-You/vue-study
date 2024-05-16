<template>
  <div class="orders">
    <div class="container">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>주문 번호</th>
            <th>주문자</th>
            <th>주소</th>
            <th>결제 수단</th>
            <th>구입 항목</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(o, idx) in state.orders" :key="idx">
            <td>{{ o.id }}</td>
            <td>{{ o.name }}</td>
            <td>{{ o.address }}</td>
            <td>{{ o.payMethod }}</td>
            <!-- <td>{{ o.orderItems }}</td> -->
            <div v-for="(i, idx2) in o.orderItems" :key="idx2">{{ i.name }}</div>
          </tr>
        </tbody>
      </table>
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
      orders: [],
    })

    axios.get('/member/order').then((res) => {
      console.log(res.data.result);
      state.orders = res.data.result;
    })

    return {
      state,
      lib,
    }
  }
}
</script>
<style scoped>
.table {
  margin-top: 30px;
  margin-bottom: 50px;
}

.table > tbody {
  border-top: 1px solid #eee;
}
</style>
