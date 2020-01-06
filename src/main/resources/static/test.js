
console.log('进来看');
$(function () {
    alert("xx");
    var E = window.wangEditor;
    var editor = new E('#editor');
//   var editor = new E( document.getElementById('editor') )
    editor.create();
})