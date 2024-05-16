<template>
  <div class="order">
    <div class="container">
      <main>
        <div class="py-5 text-center">
          <h2>주문하기</h2>
          <p class="lead">Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it.</p>
        </div>

        <div class="row g-5">
          <div class="col-md-5 col-lg-4 order-md-last">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
              <span class="text-primary">구입목록</span>
              <span class="badge bg-primary rounded-pill">
                {{ state.items.length }}
              </span>
            </h4>
            <ul class="list-group mb-3">
              <li class="list-group-item d-flex justify-content-between lh-sm" v-for="(i, idx) in state.items" :key="idx">
                <div>
                  <h6 class="my-0"> {{ i.name }}</h6>
                </div>
                <span class="text-muted">
                  {{ lib.getNumberFormat(i.price - i.price * i.discountRate / 100) }}원
                </span>
              </li>
            </ul>
            <h3 class="text-center total-price">
              총 결제 금액: {{ lib.getNumberFormat(computedPrice) }}원
            </h3>
          </div>
          <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">Billing address</h4>
            <div class="needs-validation" novalidate>
                <div class="col-12">
                  <label for="username" class="form-label">이름</label>
                  <div class="input-group has-validation">
                    <input type="text" class="form-control" id="username" v-model="state.form.name">
                    <div class="invalid-feedback">
                      Your username is required.
                    </div>
                  </div>
                </div>

                <div class="col-12">
                  <label for="address" class="form-label">주소</label>
                  <input type="text" class="form-control" id="address" v-model="state.form.address">
                  <div class="invalid-feedback">
                    Please enter your shipping address.
                  </div>
                </div>

              <hr class="my-4">

              <h4 class="mb-3">결제수단</h4>

              <div class="my-3">
                <div class="form-check">
                  <input id="card" name="payMethod" type="radio" class="form-check-input" value="card" v-model="state.form.payMethod">
                  <label class="form-check-label" for="card">신용카드</label>
                </div>
                <div class="form-check">
                  <input id="bank" name="payMethod" type="radio" class="form-check-input" value="bank" v-model="state.form.payMethod">
                  <label class="form-check-label" for="bank">무통장입금</label>
                </div>
              </div>

              <label for="cc-name" class="form-label">카드 번호</label>
              <input type="text" class="form-control" id="cc-name" v-model="state.form.cardNumber">

              <hr class="my-4">

              <button class="w-100 btn btn-primary btn-lg" @click="submit()">결제하기</button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>
<script>
import { reactive, computed } from 'vue';
import axios from '../axios'
import lib from '../scripts/lib'

export default {
  setup() {
    const state = reactive({
      items: [],
      form: {
        name: "",
        address: "",
        payMethod: "",
        cardNumber: "",
        orderItems: [
          
        ]
      }
    })

    const loadItem = () => {
      axios.get('/member/cart/item').then((res) => {
        console.log(res.data);
        state.items = res.data.result;
      })
    }

    const submit = () => {
      state.form.orderItems = state.items.map(item => ({
        itemId: item.id
      }));

      axios.post('/member/order', state.form).then((res) => {
        console.log('success');
      })
    }

    const computedPrice = computed(() => {
      let result = 0;
      
      for(let i of state.items) {
        result += i.price - i.price * i.discountRate / 100;
      }

      return result;
    })

    loadItem();

    return {
      state,
      lib,
      loadItem,
      submit,
      computedPrice,
    }
  }
}
</script>
<style scoped>

</style>
