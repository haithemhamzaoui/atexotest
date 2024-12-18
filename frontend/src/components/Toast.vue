<script>
export default {
  data() {
    return {
      visible: false,
      message: '',
    };
  },
  methods: {
    show(message) {
      this.message = message;
      this.visible = true;
      setTimeout(() => {
        this.visible = false;
      }, 3000);
    },
  },
};
</script>
<template>
  <div v-if="visible" class="toast">{{ message }}</div>
</template>
<style scoped>
.toast {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #007BFF;
  color: white;
  padding: 10px 20px;
  border-radius: 4px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>