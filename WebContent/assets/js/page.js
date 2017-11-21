var totalpage, pagesize, cpage, count, curcount, outstr;
// 初始
cpage = 1;
totalpage = "";
outstr = "";
form = "";
function jumpPage(pageNo, type) {
	
	if (1 == type) {

		document.getElementById('pageNo').value = document
				.getElementById('next').value;
	} else if (2 == type) {
		document.getElementById('pageNo').value = document
				.getElementById('pre').value;

	} else {

		document.getElementById('pageNo').value = pageNo-1;

	}
	document.forms[form].submit();
	return false;

}
function gotopage(target) {
	cpage = target; // 把页面计数定位到第几
	setpage();
	// reloadpage(target); //调用显示页面函数显示第几 这个功能是用在页面内容用ajax载入的情
}
function setpage(goform ,pagenum, allPage,totalCount) {

	totalpage = allPage;
	form = goform;
	cpage = pagenum;
	if (totalpage == 1) {
		outstr="";
	}
	if (totalpage <= 7 && totalpage > 1) { // 总页数小于七页
		for (count = 1; count <= totalpage; count++) {
			if (count != cpage) {
				outstr = outstr
						+ "<a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
						+ count + "," + 3 + ")'>" + count + "</a>";
			} else {
				outstr = outstr + "<a class='current1' style='color:#fff;'>" + count + "</a>";
			}
		}
	}
	if (totalpage > 7) { // 总页数大于七页
		if (cpage < 6) {
			for (count = 1; count <= 6; count++) {

				if (count != cpage) {
					outstr = outstr
							+ "<a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
							+ count + "," + 3 + ")'>" + count + "</a>";
				} else {
					outstr = outstr + "<a class='current1' style='color:#fff;'>" + count + "</a>";
				}
			}
			outstr = outstr
					+ "<span style='float:left;margin-left:10px; line-height:22px;'>...</span><a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+ totalpage + "," + 3 + ")'>" + totalpage + "</a>";

			outstr = outstr + "<a style='font-size:12px;' href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+ 0+"," + 1 + ")'>下一页</a>";
		} else if ((totalpage - cpage) > 3) {

			outstr = outstr + "<a style='font-size:12px;' href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+ 0+"," + 2 + ")'>上一页</a>";
			outstr = outstr + "<a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+ 1 + "," + 3 + ")'>" + 1 + "</a><span style='float:left;margin-left:10px; line-height:22px;'>...</span>";
			for (count = cpage - 3; count <= cpage + 2; count++) {
				if (count != cpage) {
					outstr = outstr
							+ "<a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
							+ count + "," + 3 + ")'>" + count + "</a>";
				} else {
					outstr = outstr + "<a class='current1' style='color:#fff;'>" + count + "</a>";
				}
			}

			outstr = outstr
					+ "<span style='float:left;margin-left:10px; line-height:22px;'>...</span><a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+ totalpage + "," + 3 + ")'>" + totalpage + "</a>";
			outstr = outstr + "<a style='font-size:12px;' href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+0 +"," + 1 + ")'> 下一页</a>";
		} else {
			outstr = outstr + "<a style='font-size:12px;' href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+0 +"," + 2 + ")'>上一页</a>";
			outstr = outstr + "<a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
					+ 1 + "," + 3 + ")'>" + 1 + "</a><span style='float:left;margin-left:10px; line-height:22px;'>...</span>";
			for (count = parseInt((cpage - 2) / 10) * 10 + 5; count <= totalpage; count++) {
				if (count != cpage) {
					outstr = outstr
							+ "<a href='javascript:void(0)' class='uncurrent1' onclick='return jumpPage("
							+ count + "," + 3 + ")'>" + count + "</a>";
				} else {
					outstr = outstr + "<a class='current1' style='color:#fff;'>" + count + "</a>";
				}
			}

		}
	}
	if(totalCount!=0&&totalCount!=-1&&totalCount!=1){
	document.getElementById("pages").innerHTML="<strong style='float:left;line-height:30px;'>共"+totalCount+"条</strong> "  +outstr;
	outstr = "";
	}
}
// setpage(); //调用分页
// -->
