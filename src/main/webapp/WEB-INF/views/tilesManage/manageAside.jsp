<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="CustomUser"/>
    <div id="sideBar" class="manageSideBar">
        <header id="header">
            <div id="profile">
                <img src="/uploads/profile/${CustomUser.employeeVO.proflPhotoFileStreNm}" alt="profileImage"/>
            </div>
            <div class="user">
                <div class="user-info">
                    <span id="departName" class="font-24 font-md">인사부</span>
                </div>
                <div class="btn-wrap">
                    <button id = "logout" class="font-11 btn-free-white"><a href="${pageContext.request.contextPath}/signOut" style="color: black;" >로그아웃<i class="icon i-signOut"></i></a></button>
                    <button id = "toHome" class="font-11 btn-free-white"><a href="${pageContext.request.contextPath}/home" style="color: black;" >사원모드<i class="icon i-home"></i></a></button>
                </div>
            </div>
        </header>
        <nav id="nav">
            <div class="hrt">
                <ul class="depth1">
                    <li class="department nav-list"><a href="#" class="active">인사팀  <i class="icon i-arr-bt"></i></a></li>
                    <ul class="depth2">
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/sanction/admin/DEPT010"><i class="icon i-sanction"></i >수신 문서함</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/employee/manageEmp"><i class="icon i-emp"></i >사원 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/vacation/manage"><i class="icon i-todo"></i >연차 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/attendance/manage"><i class="icon i-job"></i >근태 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/schedule/scheduleMain"><i class="icon i-calendar"></i>회사 일정 관리</a></li>
                    </ul>
                </ul>
            </div>
            <div class="gat">
                <ul class="depth1">
                    <li class="department nav-list"><a href="#">총무팀 <i class="icon i-arr-bt"></i></a></li>
                    <ul class="depth2">
                        <li class="depth2-nav-list"><a href="#" ><i class="icon i-sanction"></i>수신 문서함</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/notice/manage"><i class="icon i-notice"></i>공지사항 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/club/admin"><i class="icon i-share"></i>동호회 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/reserve/manageRoom"><i class="icon i-building"></i>시설 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/reserve/manageVehicle"><i class="icon i-parking"></i>차량 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/diet/dietMain"><i class="icon i-meal"></i>식단 관리</a></li>
                    </ul>
                </ul>
            </div>
            <div class="at">
                <ul class="depth1">
                    <li class="department nav-list"><a href="#">회계팀  <i class="icon i-arr-bt"></i></a></li>
                    <ul class="depth2">
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/sanction/admin/DEPT011"><i class="icon i-sanction"></i>수신 문서함</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/card/manage"><i class="icon i-card"></i>회사 카드 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/salary/calculate"><i class="icon i-pig"></i >급여 정산</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/salary/list"><i class="icon i-todo"></i>급여 명세서 관리</a></li>
                        <li class="depth2-nav-list"><a href="${pageContext.request.contextPath}/salary"><i class="icon i-money"></i >기본 급여 및 공제 관리</a></li>
                    </ul>
                </ul>
            </div>
            <div class="chart">
                <ul class="depth1">
                    <li class="department nav-list">
                        <a href="${pageContext.request.contextPath}/common/chart">통계<i class="icon"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</sec:authorize>
<script>
    var socket = null;
    $(document).ready(function() {
        connectWs();
    });

    function connectWs() {
        //웹소켓 연결
        sock = new SockJS("https://groovy.best/echo-ws");
        socket = sock;

        sock.onmessage = function(event) {
            let $socketAlarm = $("#alarm");

            //handler에서 설정한 메시지 넣어준다.
            $socketAlarm.html(event.data);
        }

        sock.onerror = function (err) {
            console.log("ERROR: ", err);
        }
    }
    /*  aside   */
    const departmentNavLinks = document.querySelectorAll('.department.nav-list');
    const activeIndex = sessionStorage.getItem('activeNavItem');

    if (activeIndex === null) {
        departmentNavLinks[0].querySelector('a').classList.add('active');
    }

    // .department.nav-list를 클릭했을 때 .depth2 높이값
    departmentNavLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            if (!link.closest('.chart')) {
                e.preventDefault();
            }

            departmentNavLinks.forEach(otherLink => {
                otherLink.querySelector('a').classList.remove('active');
            });

            link.querySelector('a').classList.add('active');

            const index = Array.from(departmentNavLinks).indexOf(link);
            sessionStorage.setItem('activeNavItem', index);

            const ul = link.nextElementSibling;

            departmentNavLinks.forEach(function (department) {
                department.classList.remove("active");
                const ul = department.nextElementSibling;
                if (ul) {
                    ul.style.maxHeight = "0";
                }
            });

            if (ul) {
                if (link.querySelector('a').classList.contains('active')) {
                    ul.style.maxHeight = ul.scrollHeight + "px";
                } else {
                    ul.style.maxHeight = "0";
                }
            }
        });
    });

    const activeLink = departmentNavLinks[activeIndex];
    const ul = activeLink.nextElementSibling;
    if (ul && activeLink.querySelector('a').classList.contains('active')) {
        ul.style.maxHeight = ul.scrollHeight + "px";
    }

    window.addEventListener('DOMContentLoaded', () => {
        const activedIndex = sessionStorage.getItem('activeNavItem');
        if (activedIndex != null) {
            departmentNavLinks.forEach(link => {
                link.querySelector("a").classList.remove("active");
            })
            const activeLink = departmentNavLinks[activedIndex];
            departmentNavLinks[activedIndex].querySelector('a').classList.add('active');
            const ul = activeLink.nextElementSibling;
            if (ul && activeLink.querySelector('a').classList.contains('active')) {
                ul.style.maxHeight = ul.scrollHeight + "px";
            }
        }
    });
</script>