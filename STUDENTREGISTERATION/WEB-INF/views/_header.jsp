<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="background: #3949AB; height: 55px; padding: 5px; color:#ecf0f1; font-family: Michroma; font-weight: bold;">
  <div style="float: left">
     <h1>Green Tech Inc</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right;">
 
     <!-- User store in session with attribute: loginedUser -->
     Hello <b>${loginedUser.userName}</b>
   <br/>
     Search <input name="search">
 
  </div>
 
</div>