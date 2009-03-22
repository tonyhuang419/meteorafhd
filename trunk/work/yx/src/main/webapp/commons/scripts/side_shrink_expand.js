function do_side_shrink()
{
document.getElementById("side_shrink").style.display = "none";
document.getElementById("side_expand").style.display = "inline";
window.parent.side_meun.cols='5,*';
}
function do_side_expand()
{
document.getElementById("side_expand").style.display = "none";
document.getElementById("side_shrink").style.display = "inline"; 
window.parent.side_meun.cols='300,*';
}
