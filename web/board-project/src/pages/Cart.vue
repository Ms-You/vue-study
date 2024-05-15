<template>
  <div class="cart">
    카트 페이지
  </div>
</template>
<script>
import axios from '../axios'

export default {
  setup() {
    axios.get('/member/cart/item').then((res) => {
      console.log(res.data);
    })

    return {
    }
  }
}
</script>
<style scoped>

</style>
