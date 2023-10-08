<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="CustomUser"/>
<style>
    .content-header {
        display: flex;
        flex-direction: column;
        gap: var(--vh-24);
    }

    .checkBtn {
        width: calc((120 / var(--vw)) * 100vw);
        height: var(--vh-64);
    }

    main {
        display: flex;
        flex-direction: column;
        gap: var(--vh-40);
    }

    #modifyRes {
        color: var(--color-font-md);
        font-size: var(--font-size-14);
        margin-left: 16px;
    }

    .form-container {
        display: flex;
        gap: var(--vw-16);
    }
</style>
<div class="content-container">
    <header id="tab-header">
        <c:if test="${page == 'info'}">
            <h1><a href="${pageContext.request.contextPath}/employee/confirm/info" class="on">내 정보 관리</a></h1>
        </c:if>
        <c:if test="${page == 'salary'}">
            <h1><a href="${pageContext.request.contextPath}/vacation">내 휴가</a></h1>
            <h1><a href="${pageContext.request.contextPath}/salary/confirm/salary" class="on">내 급여</a></h1>
            <h1><a href="${pageContext.request.contextPath}/vacation/request">휴가 기록</a></h1>
        </c:if>
        <c:if test="${page == 'email'}">
            <h1><a href="${pageContext.request.contextPath}/employee/confirm/email" class="on">메일</a></h1>
        </c:if>
    </header>

    <main>
        <div class="content-header">
            <h2 class="main-title">비밀번호 확인 🤭</h2>
            <p class="main-desc">
                개인정보 보호를 위해 비밀번호를 <br/>
                한번 더 확인합니다.
            </p>
        </div>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/employee/confirm/${page}" method="post" id="form">
                <input type="password" id="password" name="password" placeholder="PASSWORD"
                       class="userPw btn-free-white input-l"/>
                <button type="submit" class="btn-free-blue checkBtn btn">확인</button>
            </form>
        </div>
        <div id="modifyRes" class="main-desc">
            <c:if test="${not empty error}">
                ${error}
            </c:if>
        </div>
    </main>
</div>

<script>
    document.querySelector("input").addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
            document.querySelector("#form").submit();
        }
    });


    function setActionUrl() {
        let currentPage = '${page}';
        let urlMappings = {
            'info': '${pageContext.request.contextPath}/employee/confirm/info',
            'salary': '${pageContext.request.contextPath}/salary/confirm/paystub',
            'email': '${pageContext.request.contextPath}/email/all',
        };

        let url = urlMappings[currentPage];
        let form = document.querySelector("#form");
        form.action = url;
    }

    setActionUrl();

</script>