function do_menu_shrink()
{
  document.all("MenuTable").style.display = "none";
  document.all("menu_expand").style.display = "block";
  document.all("menu_shrink").style.display = "none";
  window.parent.mainframe.cols='18,*';
}
function do_menu_expand()
{
  document.all("MenuTable").style.display = "block";
  document.all("menu_expand").style.display = "none";
  document.all("menu_shrink").style.display = "block";
  window.parent.mainframe.cols='250,*';
}
