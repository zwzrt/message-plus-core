<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'

/**
 * 转换对象为buffer
 * @param o 对象
 * @returns {ArrayBuffer}
 */
function getBuffer(o) {
  const jsonObject = JSON.stringify(o);
  let bytes = Array.from(jsonObject, char => char.charCodeAt(0));
  const buffer = new ArrayBuffer(bytes.length + 2);
  const view = new DataView(buffer);
  // 请求类型
  view.setUint8(0, 1);
  // 请求内容
  for(const x in jsonObject) {
    view.setUint8(Number.parseInt(x)+1, jsonObject.charCodeAt(x));
  }
  return buffer;
}

const websocket = new WebSocket("http://localhost:8080");
//连接成功建立的回调方法
websocket.onopen = function() {
  var buffer = getBuffer({
    path: "/test/1",
    type: "1".charCodeAt(0)
  });

  // websocket.send(buffer);
  websocket.send(JSON.stringify({
    path: "/test/1",
    type: 1
  }));
}
</script>
<template>
  <header>
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>
  </header>

  <main>
    <TheWelcome />
  </main>
</template>
<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
