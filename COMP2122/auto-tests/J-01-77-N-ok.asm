segment	.text
align	4
_L1:
	push	ebp
	mov	ebp, esp
	sub	esp, 0
;; before body 
segment	.rodata
align	4
_L2:
	db	"ola", 0
segment	.data
	dd	_L2
	call	prints
	add	esp, 4
	call	println
;; after body 
	leave
	ret
segment	.data
align	4
f:
	dd	_L1
segment	.text
align	4
global	_main:function
_main:
	push	ebp
	mov	ebp, esp
	sub	esp, 0
;; before program body 
	push	dword 0
	pop	eax
	jmp	dword _L3
	pop	eax
	push	dword [eax]
	pop	eax
	call	eax
	push	eax
