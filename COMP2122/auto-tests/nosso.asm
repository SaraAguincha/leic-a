; TEXT
segment	.text
; ALIGN
align	4
; LABEL _L1
_L1:
; ENTER 0
	push	ebp
	mov	ebp, esp
	sub	esp, 0
;; before body 
; RODATA
segment	.rodata
; ALIGN
align	4
; LABEL _L2
_L2:
; SSTRING (value omitted -- see below)
	db	"ola", 0
; DATA
segment	.data
; SADDR _L2
	dd	_L2
; CALL prints
	call	prints
; TRASH 4
	add	esp, 4
; CALL println
	call	println
; LEAVE
	leave
; RET
	ret
; DATA
segment	.data
; ALIGN
align	4
; LABEL f
f:
; SADDR _L1
	dd	_L1
; TEXT
segment	.text
; ALIGN
align	4
; GLOBAL _main, :function
global	_main:function
; LABEL _main
_main:
; ENTER 0
	push	ebp
	mov	ebp, esp
	sub	esp, 0
;; before program body 
; INT 0
	push	dword 0
; STFVAL32
	pop	eax
; JMP _L3
	jmp	dword _L3
; LDINT
	pop	eax
	push	dword [eax]
; BRANCH
	pop	eax
	call	eax
; LDFVAL32
	push	eax
